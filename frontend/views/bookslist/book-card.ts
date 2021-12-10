import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('book-card')
export class BookCard extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<li class="bg-contrast-5 flex flex-col items-start p-m rounded-l" style="hegith:40rem; width:20rem">
      <div
        class="bg-contrast flex items-center justify-center mb-m overflow-hidden rounded-m w-full"
        style="height: 470px;"
      >
        <img id="image" class="w-full" />
      </div>
      <a href="#" class="text-xl font-semibold" id="header"></a>
      <span class="text-s text-secondary" id="subtitle"></span>
      <p class="my-m" id="text"></p>
      <span theme="badge" id="badge"></span>
    </li> `;
  }
}
