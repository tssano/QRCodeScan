//
// Copyright (c) 2020 Google LLC.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

import UIKit

import MLKitCommon
import MLKitEntityExtraction

@objc(ModelManagementViewController)
class ModelManagementViewController: UITableViewController {
  let languages = EntityExtractionModelIdentifier.allModelIdentifiersSorted()
  let modelManager = ModelManager.modelManager()
  var downloadedLanguages: Set<EntityExtractionModelIdentifier> = []

  override func viewDidLoad() {
    NotificationCenter.default.addObserver(
      self, selector: #selector(receiveModelLoadingDidCompleteNotification(notification:)),
      name: .mlkitModelDownloadDidSucceed, object: nil)
    NotificationCenter.default.addObserver(
      self, selector: #selector(receiveModelLoadingDidCompleteNotification(notification:)),
      name: .mlkitModelDownloadDidFail, object: nil)
    refresh()
  }

  func refresh() {
    self.downloadedLanguages = Set(
      modelManager.downloadedEntityExtractionModels.map { $0.modelIdentifier })
    self.tableView.reloadData()
  }

  override func numberOfSections(in tableView: UITableView) -> Int {
    return 1
  }

  override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return languages.count
  }

  override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath)
    -> UITableViewCell
  {
    let cellIdentifier = "language"
    let cell =
      tableView.dequeueReusableCell(withIdentifier: cellIdentifier)
      ?? UITableViewCell.init(
        style: UITableViewCell.CellStyle.default, reuseIdentifier: cellIdentifier)
    let language = languages[indexPath.row]
    let code = language.toLanguageTag()
    cell.textLabel!.text = Locale.current.localizedString(forLanguageCode: code)
    if #available(iOS 13.0, *) {
      #if swift(>=5.1)
        if downloadedLanguages.contains(language) {
          cell.imageView!.image = UIImage.init(systemName: "trash.circle")
        } else {
          cell.imageView!.image = UIImage.init(systemName: "icloud.and.arrow.down")
        }
      #endif
    } else {
      // TODO(mikie): implement status symbols for pre-13.
    }
    return cell
  }

  func showError(title: String, message: String) {
    let alert = UIAlertController.init(
      title: title, message: message,
      preferredStyle: UIAlertController.Style.alert)
    let action = UIAlertAction.init(
      title: "OK", style: UIAlertAction.Style.default,
      handler: {
        action in
      })
    alert.addAction(action)
    self.present(alert, animated: false, completion: nil)
  }

  override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    let language = languages[indexPath.row]
    let model = EntityExtractorRemoteModel.entityExtractorRemoteModel(identifier: language)
    if downloadedLanguages.contains(language) {
      modelManager.deleteDownloadedModel(model) {
        [weak self]
        error in
        guard let self = self else { return }
        if error != nil {
          self.showError(title: "Deleting model failed", message: error!.localizedDescription)
        }
        self.refresh()
      }
    } else {
      let conditions = ModelDownloadConditions(
        allowsCellularAccess: true,
        allowsBackgroundDownloading: true
      )
      modelManager.download(model, conditions: conditions)
    }
  }

  @objc
  func receiveModelLoadingDidCompleteNotification(notification: NSNotification!) {
    if notification.name == NSNotification.Name.mlkitModelDownloadDidFail {
      let userInfo = notification.userInfo!
      let error = userInfo[ModelDownloadUserInfoKey.error.rawValue] as! NSError
      DispatchQueue.main.async {
        self.showError(title: "Downloading model failed", message: error.localizedDescription)
      }
    }
    DispatchQueue.main.async { self.refresh() }
  }
}
