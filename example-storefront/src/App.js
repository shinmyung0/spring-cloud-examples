import React, { Component } from 'react';
import './elemental.css';
import './App.css';
import {Table} from 'elemental';

class App extends Component {
  render() {
    return (
      <div className="App">
          <Table>
              <colgroup>
                  <col width="10%" />
                  <col width="" />
                  <col width="10%" />
                  <col width="10%" />
              </colgroup>
              <thead>
                <tr>
                    <th>Date</th>
                    <th>Event</th>
                    <th>Price</th>
                    <th>Status</th>
                </tr>
              </thead>
              <tbody>
              <tr>
                  <td>
                      2017-04-12 18:00:00
                  </td>
                  <td>
                      SXSW
                  </td>
                  <td>
                      $149.99
                  </td>
                  <td>
                      <label>
                          <button>Buy</button>
                      </label>
                  </td>
              </tr>
              <tr>
                  <td>
                      2017-04-12 18:00:00
                  </td>
                  <td>
                      SXSW
                  </td>
                  <td>
                      $149.99
                  </td>
                  <td>
                      <label>
                          Sold
                      </label>
                  </td>
              </tr>

              </tbody>
          </Table>
      </div>
    );
  }
}

export default App;
